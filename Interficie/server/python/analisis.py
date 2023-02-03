import pymongo
import pandas as pd
from pymongo import MongoClient
import matplotlib.pyplot as plt
import os

current_directory = os.path.dirname(os.path.abspath(__file__))

PATH_STATS = os.path.join(current_directory, "stats.json")
PATH_PARTIDA = os.path.join(current_directory, "partidas.json")
PATH_IMG1 = os.path.join(current_directory, "myGraphic.png")

client = MongoClient()

client = MongoClient('mongodb://a19nargomcar2:paco1234@labs.inspedralbes.cat:7010/?tls=false&authMechanism=DEFAULT&authSource=DAMA_Grup1')

db = client['DAMA_Grup1']

partidas = db["partidas"]

statsAxe = db["axestats"]
statsWar = db["warstats"]
statsShield = db["shieldstats"]

#dataAxe = pd.DataFrame(list(statsAxe.find()))
#dataWar = pd.DataFrame(list(statsWar.find()))
#dataShield = pd.DataFrame(list(statsShield.find()))

#dataAxe.drop(dataAxe.columns[0], axis = 1, inplace = True)
#dataWar.drop(dataWar.columns[0], axis = 1, inplace = True)
#dataShield.drop(dataShield.columns[0], axis = 1, inplace = True)

#data_allStats = pd.concat([dataAxe, dataWar, dataShield])

#data_allStats.to_json(PATH_STATS, orient = "records", indent = 4)

dataP = pd.DataFrame(list(partidas.find()))

dataP.drop(dataP.columns[0], axis = 1, inplace = True)

for i, row in dataP.iterrows():
    for j in range(len(row["jugadores"])):
        row["jugadores"][j].pop("_id", None)

dataP.to_json(PATH_PARTIDA, orient="records", indent = 4)

df = pd.read_json(PATH_STATS)

print(df)

y = range(0, 5)

for i in range(df.shape[0]):
    x = df.iloc[i, :]
    plt.plot(x, y)

plt.savefig(PATH_IMG1)