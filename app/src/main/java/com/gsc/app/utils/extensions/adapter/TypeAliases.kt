package com.gsc.app.utils.extensions.adapter

import androidx.recyclerview.widget.DiffUtil

typealias DiffUtilMaker<T> = () -> DiffUtil.ItemCallback<T>