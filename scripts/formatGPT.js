export function formatGPT(prompt_reply) {

    // Exception Thrown from Server.
    if (prompt_reply.slice(0,2) === '!!') {
        return "<strong style=\"color: red;\">"+prompt_reply.slice(2)+"<strong>"
    }
    // Convert Bold: **text** -> <strong>text</strong>
    prompt_reply = prompt_reply.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');

    // Convert Italics: *text* -> <em>text</em>
    prompt_reply = prompt_reply.replace(/\*(.*?)\*/g, '<em>$1</em>');

    // Convert Strikethrough: ~~text~~ -> <s>text</s>
    prompt_reply = prompt_reply.replace(/~~(.*?)~~/g, '<s>$1</s>');

    // Convert Inline Code: `code` -> <code>code</code>
    prompt_reply = prompt_reply.replace(/`(.*?)`/g, '<br><code>$1</code><br>');

    // Convert Code Block: ```code``` -> <pre><code>code</code></pre>
    prompt_reply = prompt_reply.replace(/```([\s\S]*?)```/g, '<br><pre><code>$1</code></pre><br>');

    // Convert Blockquote: > text -> <blockquote>text</blockquote>
    prompt_reply = prompt_reply.replace(/^>\s(.*?)(\n|$)/gm, '<blockquote>$1</blockquote>');

    // Convert Headings: # Heading -> <h1>Heading</h1>
    prompt_reply = prompt_reply.replace(/^# (.*?)(\n|$)/gm, '<h1>$1</h1>');
    prompt_reply = prompt_reply.replace(/^## (.*?)(\n|$)/gm, '<h2>$1</h2>');
    prompt_reply = prompt_reply.replace(/^### (.*?)(\n|$)/gm, '<h3>$1</h3>');

    // Convert Ordered List: 1. Item -> <ol><li>Item</li></ol>
    prompt_reply = prompt_reply.replace(/^\d+\.\s(.*?)(\n|$)/gm, '<li>$1</li>');
    prompt_reply = prompt_reply.replace(/(<li>.*<\/li>)/g, '<ol>$1</ol>'); // Wrap in <ol> after converting items

    // Convert Unordered List: - Item -> <ul><li>Item</li></ul>
    prompt_reply = prompt_reply.replace(/^\-\s(.*?)(\n|$)/gm, '<li>$1</li>');
    prompt_reply = prompt_reply.replace(/(<li>.*<\/li>)/g, '<ul>$1</ul>'); // Wrap in <ul> after converting items

    // Convert Links: [text](url) -> <a href="url">text</a>
    prompt_reply = prompt_reply.replace(/\[(.*?)\]\((.*?)\)/g, '<a href="$2">$1</a><br>');

    return prompt_reply;
}

export function logFormater(prompt_reply) {

    let splited_reply = [];

    splited_reply = prompt_reply.split("#PARA#",4);

    // To see Responses in console and past responses for reference.
    console.log(splited_reply)

    return splited_reply;
    
}