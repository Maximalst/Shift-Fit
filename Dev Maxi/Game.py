import tkinter as tk
import random
import time

# Konstanten
NUM_GLAESER = 5
FARBEN = ['red', 'blue', 'green', 'yellow']
SCHICHTEN_PRO_GLAS = 4
GLAS_HOEHE = 200
SCHICHT_HOEHE = GLAS_HOEHE // SCHICHTEN_PRO_GLAS
GLAS_BREITE = 60
ABSTAND = 100

class WasserSortiererGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("Wasser Sortierer")
        self.canvas = tk.Canvas(root, width=800, height=300, bg='white')
        self.canvas.pack()

        self.glaeser = [[] for _ in range(NUM_GLAESER)]
        self.start_time = time.time()
        self.moves = 0
        self.fertig = False

        self.initialisieren()
        self.zeichnen()
        self.canvas.bind("<Button-1>", self.klick_handler)

    def initialisieren(self):
        farben_mix = FARBEN * SCHICHTEN_PRO_GLAS
        random.shuffle(farben_mix)
        for farbe in farben_mix:
            while True:
                glas = random.choice(self.glaeser)
                if len(glas) < SCHICHTEN_PRO_GLAS:
                    glas.append(farbe)
                    break

    def zeichnen(self):
        self.canvas.delete("all")
        for i, glas in enumerate(self.glaeser):
            x = ABSTAND * i + 50
            y = 250
            self.canvas.create_rectangle(x, y - GLAS_HOEHE, x + GLAS_BREITE, y, outline='black')
            for j, farbe in enumerate(reversed(glas)):
                self.canvas.create_rectangle(
                    x + 2, y - (j + 1) * SCHICHT_HOEHE,
                    x + GLAS_BREITE - 2, y - j * SCHICHT_HOEHE,
                    fill=farbe
                )
        self.canvas.create_text(400, 20, text=f"Züge: {self.moves}", font=("Arial", 14))

        if self.fertig:
            dauer = round(time.time() - self.start_time, 2)
            self.canvas.create_text(400, 280, text=f"🎉 Fertig in {self.moves} Zügen und {dauer} Sekunden! 🎉", font=("Arial", 14), fill='green')

    def klick_handler(self, event):
        if self.fertig:
            return

        index = (event.x - 50) // ABSTAND
        if 0 <= index < NUM_GLAESER:
            verschoben = self.versuche_verschieben(index)
            if verschoben:
                if self.ist_fertig():
                    self.fertig = True
            self.zeichnen()

    def versuche_verschieben(self, von_index):
        if not self.glaeser[von_index]:
            return False

        farbe = self.glaeser[von_index][-1]
        for nach_index in range(NUM_GLAESER):
            if nach_index == von_index:
                continue
            ziel = self.glaeser[nach_index]
            if len(ziel) < SCHICHTEN_PRO_GLAS and (not ziel or ziel[-1] == farbe):
                self.glaeser[nach_index].append(self.glaeser[von_index].pop())
                self.moves += 1
                return True
        return False

    def ist_fertig(self):
        for glas in self.glaeser:
            if not glas:
                continue
            if len(glas) != SCHICHTEN_PRO_GLAS or len(set(glas)) != 1:
                return False
        return True

if __name__ == "__main__":
    root = tk.Tk()
    app = WasserSortiererGUI(root)
    root.mainloop()