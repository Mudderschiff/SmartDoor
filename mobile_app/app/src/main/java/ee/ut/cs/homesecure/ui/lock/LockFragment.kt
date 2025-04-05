package ee.ut.cs.homesecure.ui.lock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ee.ut.cs.homesecure.databinding.FragmentLockBinding

class LockFragment : Fragment() {
    private var _binding: FragmentLockBinding? = null
    private val binding get() = _binding!!
    private lateinit var lockViewModel: LockViewModel
    private val viewModelLock: LockViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        lockViewModel = ViewModelProvider(this)[LockViewModel::class.java]
        _binding = FragmentLockBinding.inflate(inflater, container, false)

        _binding!!.lifecycleOwner= this
        _binding!!.viewModelLock =viewModelLock

        val textUnlocking: TextView = binding.textLock
        lockViewModel.textUnlock.observe(viewLifecycleOwner, {
            textUnlocking.text = it
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
