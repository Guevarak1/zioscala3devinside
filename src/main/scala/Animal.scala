
//Anything under animal
abstract class Animal

final case class Dog(name: String) extends Animal
//covarient
abstract class AnimalShelter[+A <: Animal]:
    def adopt(name: String): A

final class DogShelter extends AnimalShelter[Dog]:
    override def adopt(name: String): Dog = 
        Dog(name)

//contravarient
abstract class Veterinarian[-A >: Dog]:
    def diagnose(a: A): String

final class ExperiencedVet extends Veterinarian[Animal]: 
    override def diagnose(animal: Animal): String =
        s"$animal will be fine!"

//covariance
// val shelter: AnimalShelter = new DogShelter
// val animal = shelter.adopt("spot")
// println(animal)