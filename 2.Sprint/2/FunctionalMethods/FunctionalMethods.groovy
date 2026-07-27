String someText = """
-Deberíamos volver ya -instó Gared mientras los bosques se tornaban más y más oscuros a su alrededor-. Los salvajes están muertos. 
-¿Te dan miedo los muertos? -preguntó Ser Waymar Royce, insinuando apenas una sonrisa. 
-Los muertos están muertos -contestó Gared. No había mordido el anzuelo. Era un anciano de más de cincuenta años, y había visto ir y venir a muchos jóvenes señores-. No tenemos nada que tratar con ellos. 
-¿Y de veras están muertos? -preguntó Royce delicadamente-. ¿Qué prueba tenemos? 
-Will los vio -respondió Gared-. Si él dice que están muertos, no necesito más pruebas. 
-Mi madre me dijo que los muertos no cantan canciones -intervino Will. Sabía que lo iban a meter en la disputa tarde o temprano. Le habría gustado que fuera más tarde que temprano. 
-Mi ama de cría me dijo lo mismo, Will -replicó Royce-. Nunca creas nada de lo que te diga una mujer cuando estás junto a su teta. Hasta de los muertos se pueden aprender cosas. -Su voz resonó demasiado alta en el anochecer del bosque. 
-Tenemos un largo camino por delante -señaló Gared-. Ocho días, hasta puede que nueve. Y se está haciendo de noche. 
-Como todos los días alrededor de esta hora -dijo Ser Waymar Royce después de echar una mirada indiferente al cielo-. ¿La oscuridad te atemoriza, Gared? 
"""

def tokenizedText = someText.tokenize()
def splittedText = someText.split()
assert splittedText as List == tokenizedText
assert splittedText != someText.split(/-/)

// Usos de collect
def b = tokenizedText.collect { String cadena -> cadena.toLowerCase() }
def transform1 = tokenizedText.collect {
    it.toLowerCase()
}.collect {
    it.replaceAll(/[,.-]+/, "")
}.collect {
    it.replaceAll(/^.*([áéíóú]).*$/) { match ->
        String original = match[0]
        String tilde = match[1]
        String replacement = [
                'á': 'a', "é": "e", "í": "i", "ó": "o", "ú": "u"
        ][tilde]

        original.replace(tilde, replacement)
    }
}
assert transform1 != b

def transformFunc = { String s ->
    s.toLowerCase().replaceAll(/[,.-]+/, "").replaceAll(/^.*([áéíóú]).*$/) { match ->
        String original = match[0]
        String tilde = match[1]
        String replacement = [
                'á': 'a', "é": "e", "í": "i", "ó": "o", "ú": "u"
        ][tilde]

        original.replace(tilde, replacement)
    }
}
assert b.collect { transformFunc(it) } == transform1   // verdadero

// Usos de group by
List frutas = ["manzana", "pera", "kiwi", "melón", "mango"]
def grupoFrutas1 = frutas.groupBy { it }
def grupoFrutas2 = frutas.groupBy { it.size() }
def grupoFrutas3 = frutas.groupBy { it.startsWith("m") }

// Manejo de mapas
def mapa1 = [
        a: 1, b: 2, c:3
]
Map mapa2 = mapa1 << grupoFrutas3
assert mapa2.a == 1 && mapa2[false] == ["pera", "kiwi"]

def claves = mapa2.findAll {it.value == 2}.keySet()
assert claves as List == ['b']

Map conteoPalabras =  transform1.groupBy { it }.collectEntries { itemName, items ->
    [itemName, items.size()]
}
assert conteoPalabras["de"] == 9

print("End")