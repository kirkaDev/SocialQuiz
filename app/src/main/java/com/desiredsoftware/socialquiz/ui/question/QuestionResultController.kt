package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.QuestionResultPresenter
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.question.QuestionShowingController.Companion.ANSWER_IS_CORRECT_KEY
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class QuestionResultController : MvpController, QuestionResultPresenter.IQuestionResultView {
    constructor()
    constructor (args: Bundle) : super(args)

    @Inject
    @InjectPresenter
    lateinit var presenter: QuestionResultPresenter

    @ProvidePresenter
    fun provides() = presenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    lateinit var textViewResult: AppCompatTextView

    lateinit var buttonTryAgain: AppCompatButton
    lateinit var buttonNextQuestion: AppCompatButton
    lateinit var buttonSelectCategory: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.view_controller_question_result, container, false)

        buttonTryAgain = view.findViewById(R.id.buttonTryAgain)
        buttonNextQuestion = view.findViewById(R.id.buttonNextQuestion)
        buttonSelectCategory = view.findViewById(R.id.buttonSelectCategory)

        textViewResult = view.findViewById(R.id.textViewResult)

        initUI()

        return view
    }

    private fun initUI() {
        buttonTryAgain.setText(R.string.tryAgain)
        buttonNextQuestion.setText(R.string.nextQuestion)
        buttonSelectCategory.setText(R.string.selectCategory)

        presenter.showResult(args.getSerializable(ANSWER_IS_CORRECT_KEY) as Boolean)

        buttonTryAgain.setOnClickListener {
            presenter.tryAgain()
        }

        buttonNextQuestion.setOnClickListener {
            presenter.nextQuestion()
        }

        buttonSelectCategory.setOnClickListener {
            presenter.selectCategory()
            val transaction = RouterTransaction.with(CategoriesController())
            router.pushController(transaction)
        }
    }

    override fun showResult(result: String) {
        textViewResult.text = result
    }

    override fun tryAgain() {
        router.popCurrentController()
    }

    override fun nextQuestion() {
        val transaction = RouterTransaction.with(QuestionShowingController())
        router.pushController(transaction)
    }

    override fun selectCategory() {
        val transaction = RouterTransaction.with(CategoriesController())
        router.pushController(transaction)
    }

}