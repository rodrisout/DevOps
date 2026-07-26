
import argparse
import getpass
import sys
import requests



def authenticate(username: str, password: str) -> bool:
    endpoint = f"https://httpbin.org/basic-auth/{username}/{password}"
    try:
        response = requests.get(endpoint, auth=(username, password), timeout=10)
        return response.status_code == 200
    except requests.RequestException:
        print("Error de conexión al servicio de autenticación. Inténtalo de nuevo más tarde.")
        return False


def prompt_credentials() -> tuple[str, str]:
    username = input("Ingrese su nombre de usuario: ").strip()
    password = getpass.getpass("Ingrese su contraseña: ")
    return username, password


def add_task(tasks: list[dict[str, str]]) -> None:
    title = input("Ingrese el título de la tarea: ").strip()
    if not title:
        print("El título no puede estar vacío.")
        return
    tasks.append({"title": title, "status": "pendiente"})
    print(f"Tarea '{title}' agregada.")


def list_tasks(tasks: list[dict[str, str]]) -> None:
    if not tasks:
        print("No hay tareas registradas.")
        return
    for index, task in enumerate(tasks, start=1):
        print(f"{index}. {task['title']} [{task['status']}]")


def delete_task(tasks: list[dict[str, str]]) -> None:
    title = input("Ingrese el título de la tarea a eliminar: ").strip()
    if not title:
        print("El título no puede estar vacío.")
        return
    for task in tasks:
        if task["title"] == title:
            tasks.remove(task)
            print(f"Tarea '{title}' eliminada.")
            return
    print(f"No se encontró ninguna tarea con el título '{title}'.")


def show_menu() -> None:
    print("\n--- Menú de Gestión de Tareas ---")
    print("1. Agregar tarea")
    print("2. Listar tareas")
    print("3. Eliminar tarea")
    print("4. Salir")


def run_task_manager() -> None:
    tasks: list[dict[str, str]] = []
    while True:
        show_menu()
        choice = input("Elija una opción (1-4): ").strip()
        if choice == "1":
            add_task(tasks)
        elif choice == "2":
            list_tasks(tasks)
        elif choice == "3":
            delete_task(tasks)
        elif choice == "4":
            print("Saliendo del programa.")
            break
        else:
            print("Opción inválida. Por favor elija una opción entre 1 y 4.")


def parse_arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Administrador de tareas de TechCorp con autenticación básica")
    parser.add_argument("-u", "--user", dest="user", help="Nombre de usuario")
    parser.add_argument("-p", "--password", dest="password", help="Contraseña")
    return parser.parse_args()


def main() -> None:
    args = parse_arguments()
    username = args.user
    password = args.password
    max_attempts = 3

    for attempt in range(1, max_attempts + 1):
        if not username or not password:
            username, password = prompt_credentials()

        if authenticate(username, password):
            print("Autenticación exitosa.")
            run_task_manager()
            return

        remaining = max_attempts - attempt
        print(f"Credenciales incorrectas. Intentos restantes: {remaining}")
        if remaining == 0:
            break
        username = None
        password = None

    print("Se han agotado los intentos de autenticación. Saliendo.")


if __name__ == "__main__":
    main()