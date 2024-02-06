package de.rieckpil;

import java.util.Objects;

public final class Customer {
  private final String firstName;
  private final String lastName;
  private final Long id;

  Customer(
    String firstName,
    String lastName,
    Long id
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.id = id;
  }

  public String firstName() {
    return firstName;
  }

  public String lastName() {
    return lastName;
  }

  public Long id() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Customer) obj;
    return Objects.equals(this.firstName, that.firstName) &&
      Objects.equals(this.lastName, that.lastName) &&
      Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, id);
  }

  @Override
  public String toString() {
    return "Customer[" +
      "firstName=" + firstName + ", " +
      "lastName=" + lastName + ", " +
      "id=" + id + ']';
  }

}
