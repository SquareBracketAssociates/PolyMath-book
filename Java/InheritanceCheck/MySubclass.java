\begin{verbatim}
package InheritanceCheck;

public class MySubclass extends MyClass {
public MySubclass() {
    super();
}
public static void main(java.lang.String[] args)
{
    MySubclass objectB = new MySubclass();
    System.out.println("Start running MySubclass.privateSub");
    objectB.privateSub();
}
private void privateCommon()
{
    System.out.println("\t=> Executing private method in subclass.");
}
private void privateSub() {
    privateCommon();
}
protected void protectedCommon()
{
    System.out.println("\t=> Executing protected method in subclass.");
}
}
\end{verbatim}
