package com.rosseti.tmgfoosball.base

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.rosseti.tmgfoosball.ui.dialog.ErrorDialog
import com.rosseti.tmgfoosball.ui.dialog.ProgressDialog
import javax.inject.Inject

open class BaseFragment : Fragment() {
    @Inject
    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var dialog: ErrorDialog

    fun hideSoftKeyboard(windowToken: IBinder) =
        (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            windowToken,
            0
        )

    companion object {
        const val GAME_BUNDLE = "game"
        const val PLAYER_BUNDLE = "player"
    }
}