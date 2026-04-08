# 🐟 Juego de Peces

Videojuego 2D desarrollado con **libGDX** para la asignatura de Programación Multimedia y Dispositivos Móviles (2º DAM).

## Descripción

Eres **Nemo**, un pez payaso que debe sobrevivir en el océano esquivando y eliminando enemigos mientras recoges powerups. ¡Aguanta todo lo que puedas y consigue la mejor puntuación!

## 🕹Controles

| Tecla | Acción |
|-------|--------|
| ← → ↑ ↓ | Mover el pez |
| `ESPACIO` | Disparar burbuja |
| `ESC` | Pausar / Menú in-game |

## Enemigos

| Enemigo | Comportamiento | Puntos |
|---------|---------------|--------|
| 🚢 **Submarino** | Se mueve de derecha a izquierda haciendo zigzag en vertical | 5 pts |
| 🦈 **Tiburón** | Avanza hacia ti y te persigue en el eje vertical | 15 pts |
| 🐡 **Pez globo** | Avanza disparando tinta hacia el jugador | 10 pts |

> En el **Nivel 2** los enemigos aparecen más rápido y el pez globo entra en escena desde el principio.

## Powerups

| Powerup | Efecto | Duración |
|---------|--------|----------|
| 🌟 **Estrella de mar** | Escudo de invencibilidad | 5 segundos |
| 🐴 **Caballito de mar** | Velocidad x1.5 | 5 segundos |
| 🐚 **Concha** | Disparo doble | 5 segundos |

## Niveles

| Nivel | Requisito | Diferencias                                     |
|-------|-----------|-------------------------------------------------|
| **Nivel 1** | Inicio | Submarino y tiburón, aparecen cada 2s           |
| **Nivel 2** | 200 puntos | Los 3 enemigos, aparecen de forma más frecuente |

## Configuración

Desde el menú principal → **Configuración**:
- **Efectos de sonido**: activar/desactivar
- **Música de fondo**: activar/desactivar
- **Dificultad**: Fácil / Medio / Difícil (afecta a la velocidad de los enemigos)

## Puntuaciones

Al finalizar la partida puedes guardar tu nombre y puntuación. Se muestran las **10 mejores puntuaciones** de todos los tiempos.

## Menú de pausa

Pulsa `ESC` durante el juego para acceder al menú de pausa:
- Continuar partida
- Activar/desactivar sonido
- Volver al menú principal
- Salir del juego

## Tecnologías

- **Java 21**
- **libGDX 1.14.0**
- **VisUI** para la interfaz
- **Gradle** como sistema de construcción

## Ejecutar el juego

```bash
./gradlew lwjgl3:run
```
