package com.campuscoders.posterminalapp.presentation.login.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.campuscoders.posterminalapp.R
import com.campuscoders.posterminalapp.databinding.FragmentLoginBinding
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.presentation.login.LoginViewModel
import com.campuscoders.posterminalapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var prefs: CustomSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        prefs = CustomSharedPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        // ✅ Remember Me switch
        binding.switchRememberMe.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateRememberMeState(isChecked)
        }

        // ✅ Login button
        binding.loginButton.setOnClickListener {
            val terminalId = binding.etTerminalId.text.toString().trim()
            val taxId = binding.ettaxId.text.toString().trim()
            val memberStore = binding.etMemberStore.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (terminalId.isEmpty() || taxId.isEmpty() || memberStore.isEmpty() || password.isEmpty()) {
                toast(requireContext(), "Lütfen tüm alanları doldurun", true)
                return@setOnClickListener
            }

            val mainUser = MainUser(
                mainUserTerminalId = terminalId,
                mainUserTaxId = taxId,
                mainUserStoreId = memberStore,
                mainUserPassword = password
            )

            lifecycleScope.launch {
                viewModel.handleLogin(mainUser)
            }
        }
    }

    private fun setupObservers() {
        // ✅ Observe Remember Me switch
        viewModel.rememberMeChecked.observe(viewLifecycleOwner) { isChecked ->
            binding.switchRememberMe.isChecked = isChecked
        }

        // ✅ Auto-fill saved credentials if Remember Me active
        viewModel.savedLoginFields.observe(viewLifecycleOwner) { saved ->
            if (binding.switchRememberMe.isChecked && saved.isNotEmpty()) {
                binding.etTerminalId.setText(saved[getString(R.string.user_terminal_id)] ?: "")
                binding.ettaxId.setText(saved[getString(R.string.user_pan_no)] ?: "")
                binding.etMemberStore.setText(saved[getString(R.string.user_store_no)] ?: "")
                binding.etPassword.setText(saved[getString(R.string.user_password)] ?: "")
            }
        }

        // ✅ Observe login status
        viewModel.loginStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.loginButton.isEnabled = false
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true

                    val loginResult = result.data
                    if (loginResult != null) {
                        val role = loginResult.role
                        val isOffline = loginResult.isOffline
                        val mode = if (isOffline) "OFFLINE" else "ONLINE"

                        toast(requireContext(), "Login successful ($mode mode)", false)

                        when (role.lowercase()) {
                            "main_user" -> navigateToMainDashboard()
                            "terminal_user" -> navigateToTerminalDashboard()
                            else -> toast(requireContext(), "Unknown role: $role", true)
                        }
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    toast(requireContext(), result.message ?: "Login failed", true)
                }
            }
        }
    }

    private fun navigateToMainDashboard() {
        val dialog = context?.showProgressDialog(Constants.INFORMATIONS_VERIFYING)
        Handler(Looper.getMainLooper()).postDelayed({
            dialog?.dismiss()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragmentContainerView, VerificationFragment())
                .commitAllowingStateLoss()
        }, Constants.PROGRESS_BAR_DURATION.toLong())
    }

    private fun navigateToTerminalDashboard() {
        val dialog = context?.showProgressDialog(Constants.INFORMATIONS_VERIFYING)
        Handler(Looper.getMainLooper()).postDelayed({
            dialog?.dismiss()
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragmentContainerView, VerificationFragment())
                .commitAllowingStateLoss()
        }, Constants.PROGRESS_BAR_DURATION.toLong())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

