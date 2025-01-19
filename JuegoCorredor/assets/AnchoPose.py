from PIL import Image

# Ruta del archivo del sprite sheet
sprite_sheet_path = "sprite_sheet8.jpg"

# Cargar el sprite sheet
sprite_sheet = Image.open(sprite_sheet_path)

# Dimensiones de la hoja de sprites
sheet_width, sheet_height = sprite_sheet.size

# Asumimos 8 frames horizontales y 2 filas (como en el código)
frame_cols = 8
frame_rows = 2
frame_width = sheet_width // frame_cols
frame_height = sheet_height // frame_rows

# Función para calcular el ancho real de un frame
def calculate_actual_width(frame):
    pixels = frame.load()
    min_x = frame.width
    max_x = 0
    for x in range(frame.width):
        for y in range(frame.height):
            _, _, _, alpha = pixels[x, y]
            if alpha != 0:  # Verificamos el canal alfa
                min_x = min(min_x, x)
                max_x = max(max_x, x)
    return max_x - min_x + 1, min_x, max_x

# Cargar la nueva imagen del sprite sheet
sprite_sheet_path_new = "sprite_sheet8.png"
sprite_sheet = Image.open(sprite_sheet_path_new).convert("RGBA")

# Recalcular las dimensiones de la hoja de sprites
sheet_width, sheet_height = sprite_sheet.size
frame_width = sheet_width // frame_cols
frame_height = sheet_height // frame_rows

# Procesar cada frame de la primera fila
frame_widths = []
for col in range(frame_cols):
    # Extraer el frame
    x = col * frame_width
    y = 0  # Primera fila
    frame = sprite_sheet.crop((x, y, x + frame_width, y + frame_height))
    actual_width, min_x, max_x = calculate_actual_width(frame)
    frame_widths.append((actual_width, min_x, max_x))

print(frame_widths)


