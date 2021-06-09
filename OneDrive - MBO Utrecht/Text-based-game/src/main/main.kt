fun main (args: Array<String>) {

    println("Welkom bij de T-rex game! \n")

    println("Voer uw naam in:")
    var username: String? = readline()

    while (username!!.isBlank()) {
        println("Dit veld is verplicht!")
        username = readline()
    }

    println("Welkom" + username + "! \n")

    println("Vul uw leeftijd in:")
    val age = readline()!!.toInt()
    var underage: Int = 16

    if (age < underage) {
        println("U bent jammer genoeg te jong om dit spel te mogen spelen, kom later terug")
    } else {
        println("Welkom" + username + "van" + age + " jaar \n")

    }

    println("Zo'n 4,5 tot 4,6 miljard jaar geleden was de oerknal en kwam de aarde tot stand.")
    println("Naarmate de tijd werd de aarde steeds voller en mooier, jammer genoeg ook gevaarlijker.")
    println("De dinosaurussen begonnen uit te sterven behalve de T-rex, die zijn bijna onverwoestbaar zou je zeggen.")
    println("Tot er een vulkaanuitbarsting was, de T-rexen begonnen zich te schuilen en waren inmiddels al 400 jaar aan het slapen.")

}