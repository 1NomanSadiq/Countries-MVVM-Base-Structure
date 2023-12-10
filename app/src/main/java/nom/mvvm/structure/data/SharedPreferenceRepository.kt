package nom.mvvm.structure.data


interface SharedPreferenceRepository {
    var email: String
    fun clear()
}