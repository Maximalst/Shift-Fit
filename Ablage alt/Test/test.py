import pygame
print("Hallo Welt")

pygame.init()
screen = pygame.display.set_mode((400, 300))
pygame.display.set_caption("Pygame GUI Example")

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    screen.fill((30, 30, 30))
    pygame.display.flip()

pygame.quit()