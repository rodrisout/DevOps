def doblarNumero = { num -> num * 2 }

def aplicarFuncion = { lista, funcion ->
    lista.collect { funcion(it) }
}

def numeros = [1, 2, 3, 4, 5]
def resultado = aplicarFuncion(numeros, doblarNumero)
println(resultado) // Debería imprimir [2, 4, 6, 8, 10]