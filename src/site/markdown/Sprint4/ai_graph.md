# **A-I Graph**

![A-I Graph](../images/A-I_graph.png)

## **pro/cons**



The package *entities* is very concrete and stable. But Since the entities of our project won't change in a future development we will not make them more abstract .

The package *technical* seams too concrete, in a future implementation it would be good to consider making this package more abstract.

The package *view* doesn't necessarly need more modification.

The package *domain* as we can see on the graph is very stable and too concrete.  Considering that the other packages depend on *domain* we should make this package more abstract than is it now.
*Note : the classes from package entities are counted in domain since domain contains the entities package *