import tkinter as tk

root = tk.Tk()
root.title("Shift Fit")
root.geometry("1100x1000")

canvas = tk.Canvas(root, width=1100, height=1000, bg="white")
canvas.pack()

# Draw 5 reagent glasses
for i in range(5):
    x = 100 + i * 200  # Spacing between glasses
    canvas.create_rectangle(x, 300, x + 100, 700, outline="black", fill="lightblue", width=2)

label = tk.Label(root, text="Reagent Glasses")
label.pack()

root.mainloop()


