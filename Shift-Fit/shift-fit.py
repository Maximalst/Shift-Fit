import tkinter as tk


##Konfiguration
Farben = ["red", "blue", "green", "yellow"]
Schichten = 4
GLAS_ANZAHL = 5

#GUI

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

timer_label = tk.Label(button_frame, text="Zeit: 00:00", font=("Arial", 12))
timer_label.pack(pady=10)

canvas.bind("<Button-1>", klick)
zeichne()
update_timer()
root.mainloop()