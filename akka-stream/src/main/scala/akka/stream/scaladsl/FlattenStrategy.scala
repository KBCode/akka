/**
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.stream.scaladsl

import akka.stream.javadsl

/**
 * Strategy that defines how a stream of streams should be flattened into a stream of simple elements.
 */
abstract class FlattenStrategy[-T, U] {
  /** Converts this Scala DSL element to it's Java DSL counterpart. */
  def asJava[S]: javadsl.FlattenStrategy[S, U]
}

object FlattenStrategy {

  /**
   * Strategy that flattens a stream of streams by concatenating them. This means taking an incoming stream
   * emitting its elements directly to the output until it completes and then taking the next stream. This has the
   * consequence that if one of the input stream is infinite, no other streams after that will be consumed from.
   */
  def concat[T]: FlattenStrategy[Source[T, _], T] = Concat[T]()

  private[akka] final case class Concat[T]() extends FlattenStrategy[Source[T, _], T] {
    override def asJava[S]: javadsl.FlattenStrategy[S, T] =
      javadsl.FlattenStrategy.Concat[T]().asInstanceOf[javadsl.FlattenStrategy[S, T]]
  }
}
