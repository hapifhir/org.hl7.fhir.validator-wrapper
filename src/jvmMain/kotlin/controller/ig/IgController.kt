package controller.ig

interface IgController {
    suspend fun listIgs(): MutableList<String>
}