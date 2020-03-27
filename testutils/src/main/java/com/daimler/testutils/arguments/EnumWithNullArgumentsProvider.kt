package com.daimler.testutils.arguments

import android.os.Build
import androidx.annotation.RequiresApi
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream
import kotlin.reflect.KClass

@RequiresApi(Build.VERSION_CODES.N)
open class EnumWithNullArgumentsProvider<T : Enum<T>>(
    private val type: KClass<T>
) : ArgumentsProvider {

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        type.java.enumConstants?.let { values ->
            mutableListOf<T?>(*values)
                .plus(null)
                .map { Arguments.of(it) }
                .stream()
        } ?: throw IllegalArgumentException("No valid Enum given.")
}
