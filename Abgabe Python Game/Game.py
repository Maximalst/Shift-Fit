import tkinter as tk
from tkinter import messagebox
import random
import time
import subprocess
import os

# Konfiguration
FARBEN = ["red", "blue", "green", "yellow"]
SCHICHTEN = 4
GLAS_ANZAHL = 5

def generiere_startzustand():
    farben = FARBEN * SCHICHTEN
    random.shuffle(farben)

    glaeser = [[] for _ in range(GLAS_ANZAHL)]
    index = 0
    for i in range(3): 
        for _ in range(SCHICHTEN):
            glaeser[i].append(farben[index])
            index += 1
    return glaeser

def pruefe_gewonnen(glaeser):
    farben_gefunden = set()

    for glas in glaeser:
        if not glas:
            continue
        farben_im_glas = set(glas)
        if len(farben_im_glas) != 1:
            return False
        farbe = glas[0]
        if farbe in farben_gefunden:
            return False
        farben_gefunden.add(farbe)

    return len(farben_gefunden) == len(FARBEN)

def giesse(von, nach):
    global zug_anzahl, spiel_gewonnen
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
            zeichne()

# macOS only Sound und Linux Erweiterung
def spiele_gewinn_sound():
    sound_datei = os.path.join(os.path.dirname(__file__), "gewonnen.mp3")
    if os.path.exists(sound_datei):
        subprocess.Popen(["afplay", sound_datei])  

def zeichne():
    global spiel_gewonnen
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

    if not spiel_gewonnen and pruefe_gewonnen(glaeser): 
        spiel_gewonnen = True 
        dauer = int(time.time() - startzeit) 
        spiele_gewinn_sound() 
        messagebox.showinfo("Gewonnen!", 
            f"Du hast gewonnen in {zug_anzahl} Zügen und {dauer} Sekunden!") 

        # Spielzeit speichern
        global spiel_index 
        eintrag = f"{spiel_index:2d}. Züge: {zug_anzahl:2d} | Zeit: {dauer:3d}s" 
        spielzeiten.append((dauer, eintrag)) 
        spiel_index += 1 

        # Liste aktualisieren 
        spielzeit_liste.insert(tk.END, eintrag) 

        # Highscore berechnen 
        bester = min(spielzeiten, key=lambda x: x[0])[1] 
        highscore_label.config(text=f"Highscore: {bester}") 

def klick(event):
    global auswahl
    index = event.x // 130
    if index >= len(glaeser):
        return

    auswahl.append(index)

    if len(auswahl) == 2:
        giesse(auswahl[0], auswahl[1])
        auswahl = []
    else:
        zeichne()  # nur beim ersten Klick neu zeichnen

def update_timer():
    if not spiel_gewonnen:
        sekunden = int(time.time() - startzeit)
        minuten = sekunden // 60
        rest = sekunden % 60
        timer_label.config(text=f"Zeit: {minuten:02d}:{rest:02d}")
    root.after(1000, update_timer)

def neustart():
    global glaeser, auswahl, zug_anzahl, startzeit, spiel_gewonnen
    glaeser = generiere_startzustand()
    auswahl = []
    zug_anzahl = 0
    spiel_gewonnen = False
    zug_label.config(text="Züge: 0")
    startzeit = time.time()
    zeichne()

# Initialisierung
glaeser = generiere_startzustand()
auswahl = []
zug_anzahl = 0
startzeit = time.time()
spiel_gewonnen = False
spielzeiten = [] 
spiel_index = 1 

# GUI
root = tk.Tk()
root.title("Wasser Sort Puzzle – 5 Gläser, 4 Farben")

canvas = tk.Canvas(root, width=700, height=600, bg="white")
canvas.pack(side="left")

button_frame = tk.Frame(root)
button_frame.pack(side="right", fill="y", padx=10, pady=20)

restart_button = tk.Button(button_frame, text="Neu starten", command=neustart, font=("Arial", 12))
restart_button.pack(pady=10)

zug_label = tk.Label(button_frame, text="Züge: 0", font=("Arial", 12))
zug_label.pack(pady=10)

timer_label = tk.Label(button_frame, text="Zeit: 00:00", font=("Arial", 12))
timer_label.pack(pady=10)

spielzeit_label = tk.Label(button_frame, text="Letzte Spiele", font=("Arial", 12, "bold")) 
spielzeit_label.pack(pady=(20, 5))

spielzeit_liste = tk.Listbox(button_frame, width=30, height=10, font=("Courier", 10)) 
spielzeit_liste.pack() 

highscore_label = tk.Label(button_frame, text="Highscore: –", font=("Arial", 12, "bold"))
highscore_label.pack(pady=(10, 0)) 

canvas.bind("<Button-1>", klick)
zeichne()
update_timer()
root.mainloop()
