# Ahorcado2
Juego del ahorcado
El programa se trata del juego del ahorcado versión online para 2-4 jugadores. Hay un servidor central que se encarga de poner en contacto a los clientes. Para 
posteriormente elegir a uno de ellos aleatoriamente como el master del juego, es decir aquel que introduce la palabra a adivinar. Este cliente pasará a actuar como un servidor
y el resto de cliente serán el resto de jugadores. Estos enviarán letras de manera concurrente y entre letra y letra introducida cada jugador tendrá que esperar
un tiempo determindo. El servidor del jugador master recibirá esas letras, comprobará si han sido introducidas previamente y en caso negativo comprobará si están en la palabra, 
enviando un mensaje. Dicho mensaje podrá ser de dos tipos:
-Afirmativo, indicando la posicón/posiciones en la que se encuentra la letra.
-Negativo, indicando el número de fallos.
El juego termina cuando o bien, se ha acertado la palabra o bien se ha llegado al número máximo de fallos.

Normas:
1- La palabra se introducirá sin tíldes (independientemende de que sean mayúsculoas o minúsculas, el servidor las transformará todas a mayúsculas).
2- La palabra es única (sin espacios en blanco) y en castellano. Tampoco valdrán nombres propios.
3- Número de fallos máximos: 11.
4- A los jugadores se les permitirá enviar palabras, pero en caso de fallo el error contará el doble.

Consideraciones:
1- Se ha considerado introducir un temporizador para evitar que haya jugadores que introduzcan un gran número de letras en un corto periodo de tiempo.
2- Está basado en un servicio P2P.

