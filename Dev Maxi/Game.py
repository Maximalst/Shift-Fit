import tkinter as tk
import random

root = tk.Tk()
root.title("Shift Fit")
root.geometry("1100x1000")

def change_colors():
    canvas.delete("all")
    for i in range(5):
        x = 100 + i * 200  
        for j in range(5):
            y = 300 + j * 80  
            color = random.choice(colors)
            canvas.create_rectangle(x, y, x + 100, y + 80, outline="black", fill=color, width=2)

schaltf1 = tk.Button(root, text="neues Spiel", command=change_colors)
schaltf1.pack()
canvas = tk.Canvas(root, width=1100, height=1000, bg="white")
colors = ["red", "green", "blue", "yellow"]

for i in range(5):
    x = 100 + i * 200  
    for j in range(5):
        y = 300 + j * 80  
        color = random.choice(colors)
        canvas.create_rectangle(x, y, x + 100, y + 80, outline="black", fill=color, width=2)

canvas.pack()

label = tk.Label(root, text="Reagent Glasses")
label.pack()

root.mainloop()