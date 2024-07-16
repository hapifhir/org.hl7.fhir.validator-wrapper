import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

private const val DEFAULT_ENVIRONMENT: String = "dev"

class ValidatorApplicationConfig {

    companion object {
            val config = extractConfig(detectEnvironment(), HoconApplicationConfig(ConfigFactory.load()))

            private fun detectEnvironment(): String {
                return System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
            }

            /**
             * Returns default environment.
             */
            private fun handleDefaultEnvironment(): String {
                println("Falling back to default environment 'dev'")
                return DEFAULT_ENVIRONMENT
            }

        private fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config {
            val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
            return Config(
                hoconEnvironment.property("host").getString(),
                Integer.parseInt(hoconEnvironment.property("port").getString())
                )
        }
        }



}