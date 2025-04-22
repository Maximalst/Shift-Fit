import tkinter as tk
from tkinter import messagebox
import random
import time

# Konfiguration
FARBEN = ["red", "blue", "green", "yellow"]
SCHICHTEN = 4
GLAS_ANZAHL = 5

def generiere_startzustand():
    farben = FARBEN * SCHICHTEN
    random.shuffle(farben)

    glaeser = [[] for _ in range(GLAS_ANZAHL)]
    index = 0
    for i in range(3):  # Nur 3 Gläser befüllt
        for _ in range(SCHICHTEN):
            glaeser[i].append(farben[index])
            index += 1
    return glaeser

def pruefe_gewonnen():
    gefunden_farben = []

    for glas in glaeser:
        if not glas:
            continue
        if len(glas) != SCHICHTEN or len(set(glas)) != 1:
            return False
        farbe = glas[0]
        if farbe in gefunden_farben:
            return False
        gefunden_farben.append(farbe)

    return len(gefunden_farben) == len(FARBEN)

def giesse(von, nach):
    global zug_anzahl
    if von == nach or not glaeser[von] or len(glaeser[nach]) >= SCHICHTEN:
        return

    farbe = glaeser[von][-1]
    if not glaeser[nach] or glaeser[nach][-1] == farbe:
        moved = False
        while glaeser[von] and glaeser[von][-1] == farbe and len(glaeser[nach]) < SCHICHTEN:
            glaeser[nach].append(glaeser[von].pop())
            moved = True

        if moved:
            zug_anzahl += 1
            zug_label.config(text=f"Züge: {zug_anzahl}")

        if pruefe_gewonnen():
            dauer = int(time.time() - startzeit)
            print("Gewonnen!")
            print(f"Züge: {zug_anzahl}")
            print(f"Zeit: {dauer} Sekunden")
            messagebox.showinfo("🎉 Gewonnen!", f"Du hast gewonnen in {zug_anzahl} Zügen und {dauer} Sekunden!")

def zeichne():
    canvas.delete("all")
    for i in range(GLAS_ANZAHL):
        x = 50 + i * 130
        y = 100
        randstärke = 6 if auswahl and auswahl[0] == i else 3
        canvas.create_rectangle(x, y, x + 60, y + SCHICHTEN * 50, outline="black", width=randstärke)

        glas = glaeser[i]
        for j in range(len(glas)):
            farbe = glas[j]
            y_pos = y + (SCHICHTEN - j - 1) * 50
            canvas.create_rectangle(x + 2, y_pos + 2, x + 58, y_pos + 48, fill=farbe, outline=farbe)

def klick(event):
    global auswahl
    index = event.x // 130
    if index >= len(glaeser):
        return

    auswahl.append(index)

    if len(auswahl) == 2:
        giesse(auswahl[0], auswahl[1])
        auswahl = []
        zeichne()
    else:
        zeichne()

def neustart():
    global glaeser, auswahl, zug_anzahl, startzeit
    glaeser = generiere_startzustand()
    auswahl = []
    zug_anzahl = 0
    zug_label.config(text="Züge: 0")
    startzeit = time.time()
    zeichne()

# Initialisierung
glaeser = generiere_startzustand()
auswahl = []
zug_anzahl = 0
startzeit = time.time()

# GUI
root = tk.Tk()
root.title("Wasser Sort Puzzle – 5 Gläser, 4 Farben")

canvas = tk.Canvas(root, width=700, height=600, bg="white")
canvas.pack(side="left")

button_frame = tk.Frame(root)
button_frame.pack(side="right", fill="y", padx=10, pady=20)

restart_button = tk.Button(button_frame, text="🔄 Neu starten", command=neustart, font=("Arial", 12))
restart_button.pack(pady=10)

zug_label = tk.Label(button_frame, text="Züge: 0", font=("Arial", 12))
zug_label.pack(pady=10)

canvas.bind("<Button-1>", klick)
zeichne()
root.mainloop()
