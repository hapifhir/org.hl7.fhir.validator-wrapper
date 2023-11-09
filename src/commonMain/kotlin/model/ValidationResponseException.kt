package model


public class ValidationException internal constructor(
    message: String
) : Throwable(message), CopyableThrowable<ValidationException> {
    /**
     * Creates a timeout exception with the given message.
     * This constructor is needed for exception stack-traces recovery.
     */
    @Suppress("UNUSED")
    internal constructor(message: String) : this(message)

    // message is never null in fact
  }