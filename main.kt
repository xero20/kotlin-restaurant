// ${}  중괄호 안에 ~.~ 넣으면 오류 안나려나?!?
//kotlin은 java를 python 방식으로 하는 너낌
// c#과 java는 background 지식이 호환되는 느낌

//null safety
/*
var a = mapOf<String,Menu>(
  "a" to Menu(1),
  "b" to Menu(2),
)

//python에선 a["b"]를 갖고 오면 Menu(2)를 갖고 옴.
// kotlin에선 a["b"] 갖고오면 Menu?를 갖고 오고 지정 안 한 a["z"]를 갖고 와도 Menu?를 줌.
// Menu? -> key가 있으면 value 주고 없으면 null을 준다는 뜻.
*/





open class Menu(_menu: String, _price: Int) {
    var menu: String = _menu
    var price: Int = _price
}

  
open class Person(_job: String) {
   var job: String = _job
}

class Owner(_job: String, _money: Int): Person(_job) {

  var money: Int = _money

  fun receiveMoney(server: Server, daymoney: Int) {
      val s = server.job
      money+= daymoney
      println("$job receives $daymoney won from $s.")
  }

  fun paySalary(salary: Int, num: Int) {
      var total = salary*num
      println("$job pays a total salary of $total won to the Worker.")
      money-= total
  }
  fun leftMoney() {
      println("$job has $money won.")
  }
}

open class Worker(_job: String): Person(_job) {
  fun getPaid() {
      println("$job gets paid.")
  }
}

class Cook(_job: String): Worker(_job) {
  fun giveFood(server:Server) {
      var s = server.job
      println("$job gives food to the $s.")
  }
}

class Server(_job: String): Worker(_job) {

  var ownerMoney = 0
  var menulist = mutableListOf<String>()

  fun getOrder(menu:Menu,num:Int,customer:Customer) {
      var c = customer.job
      for(i in 1..num)
        menulist.add(menu.menu)
      println("$job gets order from $c.")
  }
  fun passOrder(cook:Cook) {
      var c = cook.job
      println("$job passes order to the $c.")
      cook.giveFood(this)
  }
  fun serveFood(customer:Customer) {
      var c = customer.job
      println("$job gives food to the $c.")
  }

  fun receiveMoney(csmoney:Int, customer: Customer) {
      var c = customer.job
      println("$job receives $csmoney won from $c.")
      ownerMoney+=csmoney
  }

  fun giveMoney(owner:Owner) {
      println("$job gives $ownerMoney won to the Owner.")
      owner.receiveMoney(this,ownerMoney)
      ownerMoney = 0
  }
}

class Customer(_job: String): Person(_job) {
  
  var payMoney = 0

  fun orderFood(m: Menu, num: Int=1,server:Server) {
      val menu = m.menu
      var s = server.job
      println("$job orders $num $menu to $s.")
      for(i in 1..num)
        payMoney += m.price
  }
  fun giveMoney(server:Server) {
    server.receiveMoney(payMoney,this)
    payMoney=0
  }
}

fun main() {
    val o = Owner("Owner",1000000)
    val s = Server("Server")
    val c = Cook("Cook")

    val set = Menu("정식", 5000)
    val special = Menu("특식", 7000)
    val coke = Menu("콜라", 2000)
    val soda = Menu("사이다", 2000)
    

    val c1 = Customer("c1")
    c1.orderFood(set,1,s)
    c1.orderFood(soda,1,s)
    s.passOrder(c)
    s.serveFood(c1)
    c1.giveMoney(s)

    val c23 = Customer("c23")
    c23.orderFood(special,2,s)
    c23.orderFood(coke,2,s)
    s.passOrder(c)
    s.serveFood(c23)
    c23.giveMoney(s)
    
    s.giveMoney(o)
    o.paySalary(50000,2)
    o.leftMoney()
}