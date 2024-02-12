package com.gsc.app.utils

import java.io.IOException

class NoConnectivityException(override var message: String) : IOException()