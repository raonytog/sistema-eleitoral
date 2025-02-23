# Linux
compL:
	@ javac src/*.java -d bin

runL:
	clear
	make compL
	@ java -cp bin App 

jarL:
	make compL
	@ jar -cfvm vereadores.jar -C bin/ ./src
	# @ java -jar vereadores.jar

# Windows
compW:
	@ javac .\src\*.java -d bin

runW:
	clear
	make compW
	@ java -cp bin App

jarW:
	make compW
	@ jar cfe vereadores.jar App -C bin .
	@ java -jar vereadores.jar

# OBS: a execucao precisa dos argumentos seguintes
# exemplo de execução com o jar:
# java -jar <.jar> <codigo do municipio> <csv dos candidatos> <caminho csv votacao> <data>