package dev.cnemo.personalaccounting.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import dev.cnemo.personalaccounting.MainActivity
import dev.cnemo.personalaccounting.data.DatabaseController
import dev.cnemo.personalaccounting.R
import dev.cnemo.personalaccounting.data.Date
import dev.cnemo.personalaccounting.databinding.FragmentHomeBinding
import dev.cnemo.personalaccounting.textwatchers.FloatTextWatcher
import dev.cnemo.personalaccounting.textwatchers.TextNotEmptyTextWatcher
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this.requireContext())
        super.onViewCreated(view, savedInstanceState)
        val mButtonSubmit = view.findViewById<Button>(R.id.fr_home_new_move_submit)
        val mDescription = view.findViewById<EditText>(R.id.fr_home_new_move_description)
        mDescription.addTextChangedListener(TextNotEmptyTextWatcher(mDescription, mButtonSubmit))
        val mAmount = view.findViewById<EditText>(R.id.fr_home_new_move_amount)
        mAmount.addTextChangedListener(FloatTextWatcher(mAmount, mButtonSubmit))
        val mDatePicker = view.findViewById<DatePicker>(R.id.fr_home_new_move_datepicker)
        val mCalendar = Calendar.getInstance()
        mCalendar.set(mDatePicker.year, mDatePicker.month, mDatePicker.dayOfMonth)
        mButtonSubmit.setOnClickListener {
            if (mAmount.error == null && mDescription.error == null) {
                // If no errors, create account move
                val desc = mDescription.text.toString()
                val amount: Float = mAmount.text.toString().toFloat()
                val user = (requireActivity() as MainActivity).getUser()

                createAccountMove(desc, amount, user.username, user.uuid, mCalendar.time)

                // Clean input
                mAmount.text = null
                mDescription.text = null
            } else {
                Toast.makeText(requireContext(), "Debes completar los campos sin errores", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createAccountMove(desc: String, amount: Float, partner: String, partnerId: String, date: java.util.Date){
        Toast.makeText(requireContext(), "Sending account move", Toast.LENGTH_SHORT).show()
        DatabaseController.getInstance(requireContext()).pushAccountMove(desc, amount, partner, partnerId, Date.fromDate(date))
    }
}