package cz.auderis.structure.traversal;

@FunctionalInterface
public interface NodeVisitor<E> {

    void visitNode(E node, NodeVisitorContext context);

}
