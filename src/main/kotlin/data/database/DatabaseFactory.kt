package com.example.data.database

import com.example.data.util.Constant.MONGO_DB_NAME
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

object DatabaseFactory {
    fun create(): MongoDatabase {
        // Replace the placeholders with your credentials and hostname
        val connectionString = System.getenv("MONGO_URL") ?: throw IllegalArgumentException("MONGO_URL is not set")


        /*val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()*/

        // Create a new client and connect to the server
        return MongoClient.create(connectionString).getDatabase(MONGO_DB_NAME)
    }
}