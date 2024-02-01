/**
 * Blocks the execution thread for the at least the specified time in milliseconds.
 *
 * Analogous to Java Thread.sleep
 * @param issues
 * @param line
 * @param col
 * @param message
 * @param type
 * @param level
 */
export function containsIssue(issues, line, col, message, type, level) {
    for (let index in issues) {
       if (issues[index].line === line
            && issues[index].col === col
            && issues[index].message === message
            && issues[index].type === type
            && issues[index].level === level
        ) {

            return true;
        }
    }

    return false;
}