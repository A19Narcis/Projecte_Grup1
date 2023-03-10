import pymongo
import pandas as pd
from pymongo import MongoClient
import matplotlib.pyplot as plt
import os

current_directory = os.path.dirname(os.path.abspath(__file__))

PATH_STATS = os.path.join(current_directory, "stats.json")
PATH_PARTIDA = os.path.join(current_directory, "partidas.json")
PATH_IMG1 = os.path.join(current_directory, "characters.png")
PATH_IMG2 = os.path.join(current_directory, "enemies.png")
PATH_IMG3 = os.path.join(current_directory, "punts.png")

client = MongoClient()

client = MongoClient('mongodb://a19nargomcar2:paco1234@labs.inspedralbes.cat:7010/?tls=false&authMechanism=DEFAULT&authSource=DAMA_Grup1')
#client = MongoClient('mongodb://localhost:27017/DAMA_Grup1')

db = client['DAMA_Grup1']

partidas = db["partidas"]

stats = db["stats"]

dataStats = pd.DataFrame(list(stats.find()))

dataStats.drop(dataStats.columns[0], axis = 1, inplace = True)
dataStats.drop(dataStats.columns[5], axis = 1, inplace = True)

dataStats.to_json(PATH_STATS, orient = "records", indent = 4)



dataP = pd.DataFrame(list(partidas.find()))

dataP.drop(dataP.columns[0], axis = 1, inplace = True)
dataP.drop(dataP.columns[3], axis = 1, inplace = True)

for i, row in dataP.iterrows():
    for j in range(len(row["jugadores"])):
        row["jugadores"][j].pop("_id", None)

dataP.to_json(PATH_PARTIDA, orient="records", indent = 4)

df = pd.read_json(PATH_STATS)

df_2 = pd.read_json(PATH_PARTIDA)

df_characters = df.iloc[0:3, :]
df_enemies = df.iloc[3:, :]

rango1 = df_2['puntos'].loc[df_2['puntos'].between(0, 250)].count()
rango2 = df_2['puntos'].loc[df_2['puntos'].between(251, 500)].count()
rango3 = df_2['puntos'].loc[df_2['puntos'].between(501, 750)].count()
rango4 = df_2['puntos'].loc[df_2['puntos'].between(751, 1000)].count()
rango5 = df_2['puntos'].loc[df_2['puntos'] > 1000].count()

dataPuntos = {'NombreRango': ['0-250', '251-500', '501-750', '751-1000', '>1000'],
              'Cantidad': [rango1, rango2, rango3, rango4, rango5]}

df_rangoPuntos = pd.DataFrame(dataPuntos)


dataTiempo = {'tiempo': df_2['tiempo']}

df_tiempos = pd.DataFrame(dataTiempo);

#Grafico de los personajes
df_characters.plot(kind = 'bar', x = 'nombreTipo', y=['velocidad', 'fuerza', 'vida', 'armadura'])
plt.ylim(0, 5)
plt.xticks(rotation=0)
plt.xlabel('\nTipo Jugador')
plt.savefig(PATH_IMG1)


#Grafico de los enemigos
df_enemies.plot(kind = 'bar', x = 'nombreTipo', y=['velocidad', 'fuerza', 'vida'])
plt.ylim(0, 5)
plt.xticks(rotation=0)
plt.xlabel('\nTipo Enemigo')
plt.savefig(PATH_IMG2)


#Grafico de las partidas (puntosPartidas)
df_rangoPuntos.plot(kind = 'bar', x = 'NombreRango', y='Cantidad')
plt.ylim(0, 25)
plt.xticks(rotation=0)
plt.xlabel('Puntos en las partidas')
plt.savefig(PATH_IMG3)


#Graficos partidas (tiempo)
