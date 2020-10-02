package model

actual class IGResponse {

    private var igs: MutableList<String> = mutableListOf()

    actual fun getIgs(): MutableList<String> {
        return igs
    }

    actual fun setIgs(igs: MutableList<String>): IGResponse {
        this.igs = igs
        return this
    }
}