
import com.beust.kobalt.*
import com.beust.kobalt.api.*
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.publish.bintray

val p = project {
    group = "org.functionale"
    name = "funktionale"
    artifactId = name
    version = "0.8"

    scm = Scm(url = "http://github.com/cbeust/kobalt",
            connection = "https://github.com/MarioAriasC/funKTionale.git",
            developerConnection = "git@github.com:MarioAriasC/funKTionale.git")

    dependenciesTest {
        compile("org.testng:testng:6.9.10")
    }

    assemble {
        mavenJars {}
    }

    bintray {
        publish = true
    }
}


