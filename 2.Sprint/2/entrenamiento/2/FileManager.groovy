
class FileManager {

    private Logger logger = new Logger()

    void processFile(String fileName) {

        File inputFile = new File(fileName)

        if (!inputFile.exists()) {
            logger.log("El fichero '${fileName}' no existe.")
            return
        }

        logger.log("Leyendo fichero: ${fileName}")

       
        List<String> lines = inputFile.readLines()

        logger.log("Transformando contenido")

        List<String> transformed = lines
                .findAll { !it.trim().isEmpty() }     
                .collect { it.toUpperCase() }         
       
        int totalCaracteres = transformed.inject(0) { total, linea ->
            total + linea.length()
        }

        logger.log("Total de caracteres: ${totalCaracteres}")

        String outputName = "copy_${inputFile.name}"

        logger.log("Escribiendo fichero: ${outputName}")

        new File(outputName).text = transformed.join(System.lineSeparator())

        logger.log("Proceso finalizado")
    }

    static void main(String[] args) {

        if (args.length == 0) {
            println "Uso: groovy FileManager.groovy <nombre_fichero>"
            return
        }

        FileManager manager = new FileManager()
        manager.processFile(args[0])
    }

}