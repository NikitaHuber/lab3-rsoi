package bmstu.nzagainov.gateway

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "services.system")
class PathResolver {
    lateinit var library: String
    lateinit var rating: String
    lateinit var reservation: String
}