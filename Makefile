# Linux
comp:
	@ javac src/*.java -d bin

run:
	java -jar vereadores.jar

jar:
	jar -c -f vereadores.jar -m MANIFEST.MF bin/*.class

# OBS: a execucao precisa dos argumentos seguintes
# exemplo de execução com o jar:
# java -jar <.jar> <codigo do municipio> <csv dos candidatos> <caminho csv votacao> <data>