package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import org.ablonewolf.flux.helper.NameGenerator;

/**
 * demonstrating the difference between the list and flux
 * list generation will take time until all items are fetched or inserted in the list.
 * each item in the flux can be accessed whenever the item is available; we can cancel fetching items whenever we want.
 */
public class DemonstrateListVSFlux {
    public static void main(String[] args) {

        // we will have to wait 10 seconds for the completion of name generation
        var nameList = NameGenerator.getNameList(10);
        System.out.println(nameList);

        // here, each name will be printed with the interval of 1 seconds; we won't have to wait for all names to be
        // generated unlike the scenario in the list.
        NameGenerator.getNameFlux(10)
                .subscribe(Util.subscriber("Name Subscriber"));
    }
}
