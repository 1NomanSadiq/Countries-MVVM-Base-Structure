package nom.mvvm.structure.utils

import java.io.IOException

class NoConnectivityException(override var message: String) : IOException()