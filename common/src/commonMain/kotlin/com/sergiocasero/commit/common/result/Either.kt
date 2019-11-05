package com.sergiocasero.commit.common.result

sealed class Either<L, R> {
    class Left<L, R>(val error: L) : Either<L, R>() {
        override fun toString(): String = "Left $error"
    }

    class Right<L, R>(val success: R) : Either<L, R>() {
        override fun toString(): String = "Right $success"
    }

    infix fun <Rp> bind(f: (R) -> (Either<L, Rp>)): Either<L, Rp> {
        return when (this) {
            is Left<L, R> -> Left(this.error)
            is Right<L, R> -> f(this.success)
        }
    }

    infix fun <Rp> map(f: (R) -> (Either<L, Rp>)): Either<L, Rp> {
        return when (this) {
            is Left<L, R> -> Left(this.error)
            is Right<L, R> -> f(this.success)
        }
    }

    infix fun <Rp> seq(e: Either<L, Rp>): Either<L, Rp> = e

    fun fold(error: (L) -> Unit, success: (R) -> Unit) {
        when(this) {
            is Left -> error(this.error)
            is Right -> success(this.success)
        }
    }
}
