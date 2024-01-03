package nom.mvvm.structure.ui.splash.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nom.mvvm.structure.databinding.FragmentSplashScreenBinding
import nom.mvvm.structure.utils.extensions.common.dialog
import nom.mvvm.structure.utils.extensions.common.dismissProgressDialog
import nom.mvvm.structure.utils.extensions.common.runDelayed
import nom.mvvm.structure.utils.extensions.common.showProgressDialog
import nom.mvvm.structure.utils.extensions.common.toast
import java.util.Locale

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy { FragmentSplashScreenBinding.inflate(layoutInflater) }
    private var tts: TextToSpeech? = null
    private var currentPrompt = ""
    private val speechRecognitionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val spokenText =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            spokenText?.let { handleSpokenText(it) }
        }

    private companion object {
        const val DELAY_BEFORE_PROMPT = 2000L
        const val DELAY_BEFORE_SPEECH_RECOGNITION = 3000L
        const val TTS_LANGUAGE = "en_US"
        const val TTS_PITCH = 1.0f
        const val GOOGLE_TTS_ENGINE_PACKAGE_NAME = "com.google.android.tts"
        const val PROMPT_INITIAL =
            "Please tell us about yourself and how are you feeling?"
        const val PROMPT_SUBMISSION_CONFIRMATION = "Do you want to submit the data?"
        const val SUBMISSION_CANCELLED_MESSAGE =
            "Submission has been cancelled and data is cleared"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize TTS
        initTTS()

        runDelayed(DELAY_BEFORE_PROMPT) {
            currentPrompt = PROMPT_INITIAL
            tts?.speak(currentPrompt, TextToSpeech.QUEUE_FLUSH, null, null)

            runDelayed(DELAY_BEFORE_SPEECH_RECOGNITION) {
                startSpeechRecognition()
            }
        }

        binding.btnSpeak.setOnClickListener {
            currentPrompt = PROMPT_INITIAL
            tts?.speak(currentPrompt, TextToSpeech.QUEUE_FLUSH, null, null)

            runDelayed(DELAY_BEFORE_SPEECH_RECOGNITION) {
                startSpeechRecognition()
            }
        }

        binding.btnSubmit.setOnClickListener {
            speakSubmittedData()
        }
    }

    private fun speakSubmittedData() {
        val fieldsToSpeak =
            listOf("First Name", "Last Name", "Address", "Sickness", "Age", "Other Information")
        val fieldsToSpeakValues = listOf(
            binding.etFirstName.text.toString(),
            binding.etLastName.text.toString(),
            binding.etAddress.text.toString(),
            binding.etSickness.text.toString(),
            binding.etAge.text.toString(),
            binding.etOther.text.toString()
        )

        //Speak individual non blank fields
        var hasSpokenFields = false
        for ((index, field) in fieldsToSpeak.withIndex()) {
            if (fieldsToSpeakValues[index].isNotBlank()) {
                tts?.speak(
                    "$field: ${fieldsToSpeakValues[index]}.",
                    TextToSpeech.QUEUE_ADD,
                    null,
                    null
                )
                hasSpokenFields = true
            }
        }

        //Construct summary and speak final sentence
        val spokenText = buildSpokenText(fieldsToSpeak, fieldsToSpeakValues)
        tts?.speak(spokenText, TextToSpeech.QUEUE_ADD, null, null)

        if (hasSpokenFields) {
            tts?.speak(
                "This completes the submitted data.",
                TextToSpeech.QUEUE_ADD,
                null,
                null
            )
        }
    }

    private fun buildSpokenText(
        fieldsToSpeak: List<String>,
        fieldsToSpeakValues: List<String>
    ): String {
        val spokenText = StringBuilder("Data submitted successfully.")

        if (fieldsToSpeakValues.all { it.isBlank() }) {
            spokenText.append(" No fields were filled in.")
        } else {
            val emptyFields =
                fieldsToSpeak.filterIndexed { index, _ -> fieldsToSpeakValues[index].isBlank() }
            if (emptyFields.isNotEmpty()) {
                spokenText.append(
                    " Please note that the following fields were left empty: ${
                        emptyFields.joinToString(
                            ", "
                        )
                    }"
                )
            }
        }

        return spokenText.toString()
    }

    private fun initTTS() {
        // Check if TTS is available on the device
        if (packageManager.resolveActivity(
                Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA),
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            Log.e("GoogleTTS", "Text-to-Speech not available on this device")
            toast("Text-to-Speech not available on this device")
            return
        }

        tts = TextToSpeech(this) { status ->
            when (status) {
                TextToSpeech.SUCCESS -> {
                    // Try to set the Google Text-to-Speech engine
                    val googleTTSEngine = setGoogleTTSEngine()

                    if (googleTTSEngine) {
                        // Google Text-to-Speech engine set successfully
                        Log.d("GoogleTTS", "Using Google Text-to-Speech engine")
                    } else {
                        // Fallback to the default Text-to-Speech engine
                        Log.e("GoogleTTS", "Using default Text-to-Speech engine")
                        toast("Google Text-to-Speech engine not available. Using default engine.")
                    }

                    // Set language and pitch
                    tts?.language = Locale(TTS_LANGUAGE)
                    tts?.setPitch(TTS_PITCH)
                }

                TextToSpeech.ERROR -> {
                    Log.e("GoogleTTS", "TextToSpeech initialization failed")
                    toast("TextToSpeech initialization failed")
                }
            }
        }
    }

    private fun setGoogleTTSEngine(): Boolean {
        // Check if Google Text-to-Speech engine is available
        val engines = tts?.engines
        val googleTTSEngine = engines?.find { it.name == GOOGLE_TTS_ENGINE_PACKAGE_NAME }

        return if (googleTTSEngine != null) {
            // Set the Google Text-to-Speech engine
            tts?.setEngineByPackageName(GOOGLE_TTS_ENGINE_PACKAGE_NAME)
            true
        } else {
            false
        }
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, currentPrompt)

        speechRecognitionLauncher.launch(intent)
    }

    private fun handleSpokenText(spokenText: String) {
        when {
            currentPrompt == PROMPT_INITIAL -> getDataUsingGemini(spokenText)
            currentPrompt == PROMPT_SUBMISSION_CONFIRMATION && spokenText.equals(
                "yes",
                ignoreCase = true
            ) -> binding.btnSubmit.performClick()

            currentPrompt == PROMPT_SUBMISSION_CONFIRMATION && spokenText.equals(
                "no",
                ignoreCase = true
            ) -> {
                currentPrompt = ""
                clearFormData()
                tts?.speak(
                    SUBMISSION_CANCELLED_MESSAGE,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
            }
        }
    }

    private fun getDataUsingGemini(spokenText: String) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyDohbTPxNnro04_i2E5RKTCFDTs2IH9UlY"
        )
        val prompt =
            "Given the following information in paragraph format, extract the information in the format:\n" +
                    "{'firstName': 'Noman',\n  'lastName': 'Sadiq',\n  'sickness': 'Flu',\n  'age': '25',\n" +
                    "  'address': 'Okara, Pakistan',\n  'otherInfo': 'Body pain, tiredness, bad stomach. tried to take panadol.'\n}\n\n\n" +
                    "Please note that above json is just an example to elaborate the format.\n" +
                    "Provide the response in JSON format only.\nInclude only the JSON response, excluding any irrelevant prompts or information.\n" +
                    "Return the JSON with either extracted data or empty values if information is not found for example in case of " +
                    "firstname not found you must return 'firstName': ''. You cannot return empty json like this: {}\nPrioritize accuracy and completeness of information extraction.\n" +
                    "Make sure to identify the correct or most sensitive sickness and give it as sickness and otherInfo must be in " +
                    "descriptive format but concise, each json field is a string. Below is the prompt\n"

        lifecycleScope.launch {
            showProgressDialog()
            try {
                val response = generativeModel.generateContent(prompt + spokenText)
                val start = response.text?.indexOf("{") ?: 0
                val end = response.text?.indexOf("}", start + 1) ?: 0
                Log.d("SpeechRecognition", "Response:\n${response.text}")
                val correctedText = response.text?.substring(start, end + 1) ?: ""
                val data = Gson().fromJson(correctedText, AiResponse::class.java)
                updateFormData(data)
                dismissProgressDialog()
                currentPrompt = PROMPT_SUBMISSION_CONFIRMATION
                tts?.speak(currentPrompt, TextToSpeech.QUEUE_FLUSH, null, null)
                startSpeechRecognition()
            } catch (e: Exception) {
                e.printStackTrace()
                dismissProgressDialog()
                dialog(e.localizedMessage)
            }
        }
    }

    private fun updateFormData(data: AiResponse) {
        binding.etFirstName.setText(data.firstName)
        binding.etLastName.setText(data.lastName)
        binding.etAddress.setText(data.address)
        binding.etAge.setText(data.age)
        binding.etSickness.setText(data.sickness)
        binding.etOther.setText(data.otherInfo)
    }

    private fun clearFormData() {
        binding.etFirstName.setText("")
        binding.etLastName.setText("")
        binding.etAddress.setText("")
        binding.etAge.setText("")
        binding.etSickness.setText("")
        binding.etOther.setText("")
    }

    override fun onDestroy() {
        super.onDestroy()
        tts?.let {
            it.stop()
            it.shutdown()
        }
    }
}