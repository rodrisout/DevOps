import re

OP = {"+", "-", "*", "/"}

class OperacionInvalida(Exception):
    pass


def parsear_operacion(linea):
    
    expresion = linea.strip()
    match = re.fullmatch(r"([+-]?\d+(?:\.\d+)?)\s*([+\-*/])\s*([+-]?\d+(?:\.\d+)?)", expresion)

    if not match:
        raise OperacionInvalida(
            f"formato inválido en '{linea}'. Se esperaba: <numero> <operador> <numero>"
        )

    num1_str, operador, num2_str = match.groups()

    if operador not in OP:
        raise OperacionInvalida(f"operador inválido '{operador}' en '{linea}'")

    try:
        num1 = float(num1_str)
        num2 = float(num2_str)
    except ValueError:
        raise OperacionInvalida(f"operandos no numéricos en '{linea}'")


    if num1.is_integer():
        num1 = int(num1)
    if num2.is_integer():
        num2 = int(num2)

    return num1, operador, num2


def operacion(num1, operador, num2):
    if operador == "+":
        return num1 + num2
    elif operador == "-":
        return num1 - num2
    elif operador == "*":
        return num1 * num2
    elif operador == "/":
        if num2 == 0:
            raise OperacionInvalida("División por cero.")
        return num1 / num2


def ejecutar_operacion(linea):
    num1, operador, num2 = parsear_operacion(linea)
    return operacion(num1, operador, num2)


def leer_operaciones():
    operaciones = []
    while True:
        linea = input("Ingrese una operación matemática: ").strip()
        if linea == "":
            break
        operaciones.append(linea)
    return operaciones


def modo_compilado():
    print()
    lineas = leer_operaciones()
    print()

    operaciones_parseadas = []
    for linea in lineas:
        try:
            num1, operador, num2 = parsear_operacion(linea)
            if operador == "/" and num2 == 0:
                raise OperacionInvalida("División por cero.")
            operaciones_parseadas.append((linea, num1, operador, num2))
        except OperacionInvalida as error:
            print("Error: una o más operaciones son inválidas. No se ejecutarán las operaciones.")
            print(f"Detalle: {error}")
            return

    print("Compilación exitosa. Ejecutando operaciones:\n")
    for linea, num1, operador, num2 in operaciones_parseadas:
        resultado = operacion(num1, operador, num2)
        print(f"{linea} = {resultado}")


def modo_interpretado():
    print()
    while True:
        linea = input("Ingrese una operación matemática: ").strip()
        if linea == "":
            break
        try:
            resultado = ejecutar_operacion(linea)
            print(f"Resultado: {resultado}")
        except OperacionInvalida as error:
            print(f"Error: {error}")


def main():
    modo = input("Elige el modo de ejecución (compilado/interpretado): ").strip().lower()

    if modo == "compilado":
        modo_compilado()
    elif modo == "interpretado":
        modo_interpretado()
    else:
        print("Modo no reconocido.")


if __name__ == "__main__":
    main()