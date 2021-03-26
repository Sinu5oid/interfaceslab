/*
Пусть в рамках некоторого проекта необходимо реализовать
калькулятор для выполнения четырёх арифметических действий (сложение, вычитание, умножение, деление) для:

1. Действительных чисел.
2. Комплексных чисел.
3. Кватернионов. Замечания:
    a. произведение не скалярное;
    b. деление – это умножение первого множителя на величину, обратную второму множителю.
4. Чисел по модулю N. Замечания:
    a. деление возможно, только если N – простое число.

Спроектировать интерфейс и реализовать классы для выполнения указанных операций.
 */

import com.aisystems.sinu5oid.interfaceslab.ComplexNumber;
import com.aisystems.sinu5oid.interfaceslab.ModulusNumber;
import com.aisystems.sinu5oid.interfaceslab.QuaternionNumber;
import com.aisystems.sinu5oid.interfaceslab.RealNumber;
import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleTypeException;
import com.aisystems.sinu5oid.interfaceslab.exceptions.IncompatibleValueException;
import com.aisystems.sinu5oid.interfaceslab.exceptions.UnsupportedModulusException;

public class Main {
    public static void main(String[] args) {
        RealNumber r = null;
        ModulusNumber m = null;
        ComplexNumber c = null;
        QuaternionNumber q = null;
        try {
            r = (RealNumber) new RealNumber(5).add(new RealNumber(2));
            m = (ModulusNumber) new ModulusNumber(2, 5).subtract(new ModulusNumber(-3, 5));
            c = (ComplexNumber) new ComplexNumber(2).divide(new ComplexNumber(-2));
            q = (QuaternionNumber) new QuaternionNumber(c, c).multiply(new QuaternionNumber(c, c));
        } catch (UnsupportedModulusException e) {
            System.out.println("failed to process modulus number ops");
        } catch (IncompatibleTypeException e) {
            System.out.println("got incompatible types: " + e.getMessage());
        } catch (IncompatibleValueException  e) {
            System.out.println("got incompatible values");
        }

        assert r != null;
        assert m != null;
        assert c != null;
        assert q != null;
        System.out.println("done");
    }
}
