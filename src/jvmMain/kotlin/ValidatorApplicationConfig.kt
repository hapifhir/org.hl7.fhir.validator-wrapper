import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import java.io.File


class ValidatorApplicationConfig {

    companion object {
        val configFilePath = System.getenv("VALIDATOR_CONFIG_FILE_PATH");
        val configSource = if (configFilePath != null) {
            ConfigFactory.parseFile(File(configFilePath)).withFallback(ConfigFactory.load())
        } else {
            ConfigFactory.load()
        }

        val ktorConfig = extractKtorConfig(HoconApplicationConfig(configSource))
        val validationServiceConfig = extractValidationServiceConfig(configSource)

        private fun extractValidationServiceConfig(config: Config): ValidationServiceConfig {
            val validationServiceConfig = config.getConfig("validation-service")
            return ValidationServiceConfig(
                validationServiceConfig.getString("presets-file-path"),
                validationServiceConfig.getInt("engine-reload-threshold")
            )
        }

        private fun extractKtorConfig(hoconConfig: HoconApplicationConfig): KtorConfig {
            val environment = hoconConfig.property("ktor.environment").getString()
            val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
            return KtorConfig(
                hoconEnvironment.property("host").getString(),
                Integer.parseInt(hoconEnvironment.property("port").getString())
            )
        }
    }


}