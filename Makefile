# Linux
comp:
	@ javac src/*.java -d bin

run:
	java -jar vereadores.jar

jar:
	jar -c -f vereadores.jar -m MANIFEST.MF bin/*.class

# Windows
compW:
	@ javac .\src\*.java -d bin

runW:
	clear
	make compW
	@ java -cp bin Main

jarW:
	make compW
	@ jar cfe vereadores.jar Main -C bin .
	@ java -jar vereadores.jar

# OBS: a execucao precisa dos argumentos seguintes
# exemplo de execução com o jar:
# java -jar <.jar> <codigo do municipio> <csv dos candidatos> <caminho csv votacao> <data>