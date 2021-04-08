package controller

import controller.ig.IgController
import controller.ig.IgControllerImpl
import controller.terminology.TerminologyController
import controller.terminology.TerminologyControllerImpl
import controller.validation.ValidationController
import controller.validation.ValidationControllerImpl
import controller.version.VersionController
import controller.version.VersionControllerImpl
import org.koin.dsl.module

object ControllersInjection {
    val koinBeans = module {
        single<ValidationController> { ValidationControllerImpl() }
        single<VersionController> { VersionControllerImpl() }
        single<IgController> { IgControllerImpl() }
        single<TerminologyController> { TerminologyControllerImpl() }
    }
}