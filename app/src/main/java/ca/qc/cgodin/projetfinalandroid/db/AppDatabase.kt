package ca.qc.cgodin.projetfinalandroid.db

import android.content.Context
import androidx.room.*
import ca.qc.cgodin.projetfinalandroid.models.publications.Publication
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur

@Database(
    entities = [Utilisateur::class, Publication::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUtilisateurDao(): UtilisateurDao
    abstract fun getPublicationDao(): PublicationDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_db.db"
            ).build()
    }
}