package ru.netology.nmedia.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.dto.MapMarker

class EditMapMarkerResultContract : ActivityResultContract <MapMarker, String?>() {

    override fun createIntent(context: Context, input: MapMarker): Intent {
       val intent  = Intent(context, EditMapMarkerFragment::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, input.description)
        return intent
    }


    override fun parseResult(resultCode: Int, intent: Intent?): String? =
      if (resultCode == Activity.RESULT_OK) {
        intent?.getStringExtra(Intent.EXTRA_TEXT)
      } else {
          null
      }


}