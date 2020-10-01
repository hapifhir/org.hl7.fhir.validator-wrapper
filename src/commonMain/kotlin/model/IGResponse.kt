package model

expect class IGResponse() {
    fun getIgs(): MutableList<String>
    fun setIgs(igs: MutableList<String>): IGResponse
}