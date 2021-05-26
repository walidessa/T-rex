fun main(args: Array<String>) {
    val game=Branch("Je begint in de tijd van de dino’s 30 miljoen jaar geleden. Je loopt rond en je bent aan het observeren en daarna zie je een T-rex. Hij slaapt maar je maakt hem perongeluk wakker. Tip: zoek de magische plant"){
        WalkNorth() leadsTo TerminalBranch("Level 1:  je moet uit de buurt blijven van de T-rex.")
        WalkSouth() leadsTo TerminalBranch("Je bent tegen een muur aangelopen, de T-rex heeft je. GAME OVER!")
        WalkEast() leadsTo Branch("Voor je vind je de plant, eet deze om power te krijgen"){
            OpenAction() leadsTo TerminalBranch("")
            val startOfBridgeref:RefBranch=RefBranch(Branch())
            val startOfBridge=Branch("Je loopt richting een heel oude brug, je staat midden op de brug en kijkt om je heen."){
                WalkNorth() leadsTo Branch(""){
                    WalkSouth() leadsTo startOfBridgeref
                    WalkNorth() leadsTo Branch("Gefeliciteerd je hebt het gehaald!")
                }
            }
            startOfBridgeref.ref=startOfBridge

            WalkNorth() leadsTo startOfBridge
        }
    }
    game.process()

}

interface Action{
    val label:String
    fun accept(input :String):Boolean
}

abstract class SimpleAction(override val label: String):Action{
    override fun accept(input: String) = label == input
}

class OpenAction:SimpleAction("OPEN")
class CloseAction:SimpleAction("DICHT")
class HitAction:SimpleAction("RAAK")

abstract class WalkAction(val direction:String):Action{
    override val label: String = "LOOP $direction"
    override fun accept(input: String): Boolean {
        val (iaction,idirection)=input.replace(Regex.fromLiteral(" {2,}")," ").split(" ")
        return iaction=="LOOP" && direction==idirection
    }
}

class WalkNorth:WalkAction("Voren")
class WalkSouth:WalkAction("Achter")
class WalkEast:WalkAction("Links")
class WalkWest:WalkAction("Rechts")

interface IBranch {
    val description: String
    val fn: IBranch.() -> Unit
    fun process()

    infix fun Action.leadsTo(brc: IBranch)
}


open class Branch(override val description:String="", override val fn:IBranch.()->Unit={}) : IBranch {
    val actions:MutableList<Pair<Action,IBranch>> = mutableListOf()
    var visited=false
    override fun process()  {
        if (!visited) this.fn()
        visited=true

        println(description)
        print("Het volgende is aanwezig:  \n- ${actions.map { it.first.label }.joinToString("\n- ")}\n> ")
        var input= readLine() ?: ""
        var processed  = actions.find { it.first.accept(input) }?.second?.run { process(); true }?:false
        while (!processed){
            input= readLine() ?: ""
            processed = actions.find { it.first.accept(input) }?.second?.run { process(); true }?:false
        }
    }
    override infix fun Action.leadsTo(brc:IBranch){
        actions.add(this to brc)
    }
}

class RefBranch(var ref:Branch=Branch()):IBranch {
    override val fn: IBranch.() -> Unit
        get() = ref.fn
    override val description: String
        get() = ref.description

    override fun process() {
        ref.process()
    }

    override fun Action.leadsTo(brc: IBranch) {
        ref.actions.add(this to brc)
    }

}

class TerminalBranch(val ending:String):Branch(){
    override fun process(){
        println(ending)
    }
}
